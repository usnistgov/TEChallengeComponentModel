package com.AuctionMaven;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;
//Double-auction mechanism for the 5-minute markets in te30 and sgip1 examples

//The substation_loop module manages one instance of this class per GridLAB-D substation.

//Todo:
//    * Initialize and update price history statistics
//    * Allow for adjustment of clearing_scalar
//    * Handle negative price bids from HVAC agents, currently they are discarded
//    * Distribute marginal quantities and fractions; these are not currently applied to HVACs
public class SimpleAuction{
	//This class implements a simplified version of the double-auction market embedded in GridLAB-D.

    //References:
    //    `Market Module Overview - Auction <http://gridlab-d.shoutwiki.com/wiki/Market_Auction>`_

    //Args:
    //    dict: a row from the agent configuration JSON file
    //    key: the name of this agent, which is the market key from the agent configuration JSON file

    //Attributes:
    //    name: the name of this auction, also the market key from the configuration JSON file
    //    std_dev: the historical standard deviation of the price, in $/kwh, from dict
    //    mean: the historical mean price in $/kwh, from dict
    //    period: the market clearing period in seconds, from dict
    //    pricecap: the maximum allowed market clearing price, in $/kwh, from dict
    //    max_capacity_reference_bid_quantity:
    //    statistic_mode: always 1, not used, from dict
    //    stat_mode: always ST_CURR, not used, from dict
    //    stat_interval: always 86400 seconds, for one day, not used, from dict
    //    stat_type: always mean and standard deviation, not used, from dict
    //    stat_value: always zero, not used, from dict
    //    curve_buyer: data structure to accumulate buyer bids
    //    curve_seller: data structure to accumulate seller bids
    //    refload: the latest substation load from GridLAB-D
    //    lmp: the latest locational marginal price from the bulk system market
    //    unresp: unresponsive load, i.e., total substation load less the bidding, running HVACs
    //    agg_unresp: aggregated unresponsive load, i.e., total substation load less the bidding, running HVACs
    //    agg_resp_max: total load of the bidding HVACs
    //    agg_deg: degree of the aggregate bid curve polynomial, should be 0 (zero or one bids), 1 (2 bids) or 2 (more bids)
    //    agg_c2: second-order coefficient of the aggregate bid curve
    //    agg_c1: first-order coefficient of the aggregate bid curve
    //    clearing_type (ClearingType): describes the solution type or boundary case for the latest market clearing
    //    clearing_quantity: quantity at the last market clearing
    //    clearing_price: price at the last market clearing
    //    marginal_quantity: quantity of a partially accepted bid
    //    marginal_frac: fraction of the bid quantity accepted from a marginal buyer or seller 
    //    clearing_scalar: used for interpolation at boundary cases, always 0.5
        
	private String name;
	public double std_dev;
	public double mean;
	public int period;
	private double pricecap;
	private int max_capacity_reference_bid_quantity;
	private int statistic_mode;
	private ArrayList<String> stat_mode;
	private ArrayList<Double> stat_interval;
	private ArrayList<String> stat_type;
	private ArrayList<Integer> stat_value;
	Curve curve_buyer;
	Curve curve_seller;
	private double refload;
	private double lmp;
	private double unresp;
	public double agg_unresp;
	public double agg_resp_max;
	public int agg_deg;
	public double agg_c2;
	public double agg_c1;
	ClearingType clearing_type;
	private double clearing_quantity;
	public double clearing_price;
	private double marginal_quantity;
	private double marginal_frac;
	private double clearing_scalar;
	private double unresponsive_sell; 
	private double responsive_sell; 
	private double unresponsive_buy; 
	private double responsive_buy;
	
	// ====================Define instance variables ===================================
	SimpleAuction(Map<String, Object> dict, String key) throws Exception
	{
		this.name = key;
		this.std_dev = (double) dict.get("init_stdev");
		this.mean = (double) dict.get("init_price");
		this.period = (int) dict.get("period");
		this.pricecap = (double) dict.get("pricecap");
		this.max_capacity_reference_bid_quantity = (int) dict.get("max_capacity_reference_bid_quantity");
		this.statistic_mode = (int) dict.get("statistic_mode");
		this.stat_mode = (ArrayList<String>) dict.get("stat_mode");
		this.stat_interval = (ArrayList<Double>) dict.get("stat_interval");
		this.stat_type = (ArrayList<String>) dict.get("stat_type");
		this.stat_value = (ArrayList<Integer>) dict.get("stat_value");
		// updated in collect_agent_bids, used in clear_market
		this.curve_buyer = null;
		this.curve_seller = null;

		this.refload = 0.0;
		this.lmp = this.mean;
		this.unresp = 0.0;
		this.agg_unresp = 0.0;
		this.agg_resp_max = 0.0;
		this.agg_deg = 0;
		this.agg_c2 = 0.0;
		this.agg_c1 = 0.0;
		this.clearing_type = ClearingType.NULL;
		this.clearing_quantity = 0.0;
		this.clearing_price = this.mean;
		this.marginal_quantity = 0.0;
		this.marginal_frac = 0.0;
		this.clearing_scalar = 0.5;

	}
	public void set_refload(double kw){
		//Sets the refload attribute

        //Args:
        //    kw: GridLAB-D substation load in kw
            
		this.refload = kw;
	}
	public void set_lmp(double lmp){
		//Sets the lmp attribute

        //Args:
        //    lmp: locational marginal price from the bulk system market
            
		this.lmp = lmp;
	}
	public void initAuction(){
		//Sets the clearing_price and lmp to the mean price
		
		this.clearing_price = this.lmp = this.mean;
	}
	public void update_statistics(){
		//Update price history statistics - not implemented
		
		int sample_need = 0;
	}
	public void clear_bids(){
		//Re-initializes curve_buyer and curve_seller, sets the unresponsive load estimate to the total substation load.
		
		this.curve_buyer = new Curve();
		this.curve_seller = new Curve();
		this.unresp = this.refload;
	}
	public void collect_bid(List<Object> bid){
		//Gather HVAC bids into curve_buyer

        //Also adjusts the unresponsive load estimate, by subtracting the HVAC power
        //if the HVAC is on.

        //Args:
        //    bid: price in $/kwh, quantity in kW and the HVAC on state
            
		double price = (double) bid.get(0);
		double quantity = (double) bid.get(1);
		boolean is_on = (boolean) bid.get(2);
		if(is_on){
			this.unresp -= quantity;
		}
		if(price > 0.0){
			this.curve_buyer.add_to_curve(price, quantity, is_on);
		}
	}
	public void aggregate_bids() throws Exception
	{
		//Aggregates the unresponsive load and responsive load bids for submission to the bulk system market
		
		File txtfile = new File(SimpleAuction.class.getResource("/").toURI());
		BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile + "/" + "simple_auction.txt", true));
		if(this.unresp > 0){
			this.curve_buyer.add_to_curve(this.pricecap, this.unresp, true);
		}else{
			//System.out.println("## unresp < 0" + " " + this.unresp + " " + this.curve_buyer.count +
			//		" " + this.curve_buyer.total + " " + this.curve_buyer.total_on + " " + this.curve_buyer.total_off);
			bw.write("## unresp < 0	" + this.unresp + "	" + this.curve_buyer.count +
					"	" + this.curve_buyer.total + "	" + this.curve_buyer.total_on + "	" + this.curve_buyer.total_off);
			bw.newLine();
			bw.flush();
		}
		if(this.curve_buyer.count > 0){
			this.curve_buyer.set_curve_order("descending");
		}
		ArrayList bid = aggregate_bid(this.curve_buyer);
		this.agg_unresp = (double) bid.get(0); 
		this.agg_resp_max = (double) bid.get(1); 
		this.agg_deg = (int) bid.get(2);
		this.agg_c2 = (double) bid.get(3);
		this.agg_c1 = (double) bid.get(4);
		bw.close();
	}

	public ArrayList aggregate_bid(Curve crv){
		//aggregates the buyer curve into a quadratic or straight-line fit with zero intercept

	    //Args:
	    //    crv: the accumulated buyer bids

	    //Returns:
	    //    [double, double, int, double, double]: Qunresp, Qmaxresp, degree, c2 and c1 scaled to MW instead of kW. c0 is always zero.
	        
		double unresp = 0.0;
		int idx = 0;
		List<Double> p = new ArrayList<Double>();
		List<Double> q = new ArrayList<Double>();
		for(int i=0; i<crv.price.size(); i++){
			p.add(crv.price.get(i) * 1000.0);
		}
		for(int i=0; i<crv.quantity.size(); i++){
			q.add(crv.quantity.get(i) * 0.001);
		}
		if(p.size()>0){
			idx = p.lastIndexOf(p.get(0));
			for(int i=0; i<=idx; i++){
				unresp += q.get(i);
			}
		}
		double c2 = 0.0;
		double c1 = 0.0;
		double qmax = 0.0;
		int deg = 0;
		List<Double> qresp = new ArrayList<Double>();
		List<Double> presp = new ArrayList<Double>();
		
		List<Double> qTemp = new ArrayList<Double>();
		
		
		double vz = 0.0;
		double vvz = 0.0;
		
		int n = p.size() - idx -1;
		
		if(n < 1){
			qmax = 0;
			deg = 0;
		}else{
			for(int i=(idx+1); i<q.size(); i++){
				vz += q.get(i);
				qresp.add(vz);
				qTemp.add(q.get(i));
			}
			for(int i=(idx+1); i<p.size(); i++){
				presp.add(p.get(i));
			}
			qmax = qresp.get(qresp.size() - 1);
			double[] cost = new double[presp.size()];
			for(int i=0; i<presp.size(); i++){
				vvz += presp.get(i) * qTemp.get(i);
				cost[i] = vvz;
			}
			RealVector rv = MatrixUtils.createRealVector(cost);
			if(n <= 2){
				double[][] A = new double[qresp.size()][1];
				for(int i=0; i<qresp.size(); i++) A[i][0] = qresp.get(i);
				RealMatrix matrix = MatrixUtils.createRealMatrix(A);
				SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
				DecompositionSolver ds = svd.getSolver();
				double[] ret  = ds.solve(rv).toArray();
				c1 = ret[0];
				deg = 1;
			}else{
				double[][] A = new double[qresp.size()][2];
				for(int i=0; i<qresp.size(); i++){
					A[i][0] = Math.pow(qresp.get(i),2);
					A[i][1] = qresp.get(i);
				}
				RealMatrix matrix = MatrixUtils.createRealMatrix(A);
				SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
				DecompositionSolver ds = svd.getSolver();
				double[] ret  = ds.solve(rv).toArray();
				c2 = ret[0];
				c1 = ret[1];
				deg = 2;	
			}
		}
		ArrayList bid = new ArrayList();
		bid.add(unresp);
		bid.add(qmax);
		bid.add(deg);
		bid.add(c2);
		bid.add(c1);
		return bid;
	}
	
	public void clear_market(int tnext_clear, int time_granted) throws Exception
	{
		//Solves for the market clearing price and quantity

        //Uses the current contents of curve_seller and curve_buyer.
        //Updates clearing_price, clearing_quantity, clearing_type,
        //marginal_quantity and marginal_frac.

        //Args:
        //    tnext_clear (int): next clearing time in FNCS seconds, should be <= time_granted, for the log file only
        //    time_granted (int): the current time in FNCS seconds, for the log file only
            
		File txtfile = new File(SimpleAuction.class.getResource("/").toURI());
		BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile + "/" + "simple_auction.txt", true));
		double bid_offset = 0.0;//variable is undefined in Python. Set to 0 for testing
		this.curve_seller.add_to_curve(this.lmp, this.max_capacity_reference_bid_quantity, true);
		if(this.curve_seller.count > 0){
			this.curve_seller.set_curve_order("ascending");
		}
		this.unresponsive_sell = this.responsive_sell = this.unresponsive_buy = this.responsive_buy = 0.0;
		if(this.curve_buyer.count > 0 && this.curve_seller.count > 0){
			double a = this.pricecap;
			double b = -this.pricecap;
			int check = 0;
			double demand_quantity = 0.0;
			double supply_quantity = 0.0;
			for(int i=0; i<this.curve_seller.count; i++){
				if(this.curve_seller.price.get(i) == this.pricecap){
					this.unresponsive_sell += this.curve_seller.quantity.get(i);
				}else{
					this.responsive_sell += this.curve_seller.quantity.get(i);
				}
			}
			for(int i=0; i<this.curve_buyer.count; i++){
				if(this.curve_buyer.price.get(i) == this.pricecap){
					this.unresponsive_buy += this.curve_buyer.quantity.get(i);
				}else{
					this.responsive_buy += this.curve_buyer.quantity.get(i);
				}
			}
			// Calculate clearing quantity and price here
            // Define the section number of the buyer and the seller curves respectively as i and j
			int i = 0;
			int j = 0;
			this.clearing_type = ClearingType.NULL;
			this.clearing_quantity = this.clearing_price = 0.0;
			while(i < this.curve_buyer.count && j < this.curve_seller.count && this.curve_buyer.price.get(i) >= this.curve_seller.price.get(j)){
				double buy_quantity = demand_quantity +this.curve_buyer.quantity.get(i);
				double sell_quantity = supply_quantity + this.curve_seller.quantity.get(j);
				// If marginal buyer currently:
				if(buy_quantity > sell_quantity){
					this.clearing_quantity = supply_quantity = sell_quantity;
					a = b = this.curve_buyer.price.get(i);
					j += 1;
					check = 0;
					this.clearing_type = ClearingType.BUYER;
				}else if(buy_quantity < sell_quantity){// If marginal seller currently:
					this.clearing_quantity = demand_quantity = buy_quantity;
					a = b = this.curve_seller.price.get(j);
					i += 1;
					check = 0;
					this.clearing_type = ClearingType.SELLER;
				}else{// Buy quantity equal sell quantity but price split
					this.clearing_quantity = demand_quantity = supply_quantity = buy_quantity;
		            a = this.curve_buyer.price.get(i);
		            b = this.curve_seller.price.get(j);
		            i += 1;
		            j += 1;
		            check = 1;
				}
			}
			// End of the curve comparison, and if EXACT, get the clear price
			if(a == b){
				this.clearing_price = a;
			}
			// If there was price agreement or quantity disagreement
			if(check == 1){
				this.clearing_price = a;
				if(supply_quantity == demand_quantity){
					// At least one side exhausted at same quantity
					if(i == this.curve_buyer.count || j == this.curve_seller.count){
						if(a == b){
							this.clearing_type = ClearingType.EXACT;
						}else{
							this.clearing_type = ClearingType.PRICE;
						}
					}else if(i == this.curve_buyer.count && b == this.curve_seller.price.get(j)){// Exhausted buyers, sellers unsatisfied at same price
						this.clearing_type = ClearingType.SELLER;
					}else if(j == this.curve_seller.count && a == this.curve_buyer.price.get(i)){// Exhausted sellers, buyers unsatisfied at same price
						this.clearing_type = ClearingType.BUYER;
					}else{// Both sides satisfied at price, but one side exhausted
						if(a == b){
							this.clearing_type = ClearingType.EXACT;
						}else{
							this.clearing_type = ClearingType.PRICE;
						}
					}    
				}else{// No side exhausted
					// Price changed in both directions
					if(a != this.curve_buyer.price.get(i) && b != this.curve_seller.price.get(j) && a == b){
						this.clearing_type = ClearingType.EXACT;
					}else if(a == this.curve_buyer.price.get(i) && b != this.curve_seller.price.get(j)){// Sell price increased ~ marginal buyer since all sellers satisfied
						this.clearing_type = ClearingType.BUYER;
					}else if(a != this.curve_buyer.price.get(i) && b == this.curve_seller.price.get(j)){// Buy price increased ~ marginal seller since all buyers satisfied
						this.clearing_type = ClearingType.SELLER;
		                this.clearing_price = b; // use seller's price, not buyer's price
					}else if(a == this.curve_buyer.price.get(i) && b == this.curve_seller.price.get(j)){// Possible when a == b, q_buy == q_sell, and either the buyers or sellers are exhausted
						if(i == this.curve_buyer.count && j == this.curve_seller.count){
							this.clearing_type = ClearingType.EXACT;
						}else if(i == this.curve_buyer.count){
							this.clearing_type = ClearingType.SELLER;
						}else if(j == this.curve_seller.count){
							this.clearing_type = ClearingType.BUYER;
						}
					}else{// Marginal price
						this.clearing_type = ClearingType.PRICE;
					}
				}
				// If ClearingType.PRICE, calculate the clearing price here
				double dHigh = 0.0;
				double dLow = 0.0;
				if(this.clearing_type == ClearingType.PRICE){
					double avg = (a+b)/2.0;
					// Calculating clearing price limits: 
					if(i == this.curve_buyer.count){
						dHigh = a;
					}else{
						dHigh = this.curve_buyer.price.get(i);
					}
					if(j == this.curve_seller.count){
						dLow = b;
					}else{
						dLow = this.curve_seller.price.get(j);
					}
					// Needs to be just off such that it does not trigger any other bids
					
					if(a == this.pricecap && b != -this.pricecap){
						if(this.curve_buyer.price.get(i) > b){
							this.clearing_price = this.curve_buyer.price.get(i) + bid_offset;
						}else{
							this.clearing_price = b;
						}       
					}else if(a != this.pricecap && b == -this.pricecap){
						if(this.curve_seller.price.get(j) < a){
							this.clearing_price = this.curve_seller.price.get(j) - bid_offset;
						}else{
							this.clearing_price = a;
						}  
					}else if(a == this.pricecap && b == -this.pricecap){
						if(i == this.curve_buyer.count && j == this.curve_seller.count){
							this.clearing_price = 0.0; // no additional bids on either side
						}else if(j == this.curve_seller.count){// buyers left
							this.clearing_price = this.curve_buyer.price.get(i) + bid_offset;
						}else if(i == this.curve_buyer.count){// sellers left
							this.clearing_price = this.curve_seller.price.get(j) - bid_offset;
						}else{// additional bids on both sides, just no clearing
							this.clearing_price = (dHigh + dLow)/2;
						}
                            
					}else{
						if(i != this.curve_buyer.count && this.curve_buyer.price.get(i) == a){
							this.clearing_price = a;
						}else if(j != this.curve_seller.count && this.curve_seller.price.get(j) == b){
							this.clearing_price = b;
						}else if(i != this.curve_buyer.count && avg < this.curve_buyer.price.get(i)){
							this.clearing_price = dHigh + bid_offset;
						}else if(j != this.curve_seller.count && avg > this.curve_seller.price.get(j)){
							this.clearing_price = dLow - bid_offset;
						}else{
							this.clearing_price = avg;
						}
					}
				}
			}
			// Check for zero demand but non-zero first unit sell price
			if(this.clearing_quantity == 0){
				this.clearing_type = ClearingType.NULL;
				if(this.curve_seller.count > 0 && this.curve_buyer.count == 0){
					this.clearing_price = this.curve_seller.price.get(0) - bid_offset;
				}else if(this.curve_seller.count == 0 && this.curve_buyer.count > 0){
					this.clearing_price = this.curve_buyer.price.get(0) + bid_offset;
				}else{
					if(this.curve_seller.price.get(0) == this.pricecap){
						this.clearing_price = this.curve_buyer.price.get(0) + bid_offset;
					}else if(this.curve_seller.price.get(0) == -this.pricecap){
						this.clearing_price = this.curve_seller.price.get(0) - bid_offset;
					}else{
						this.clearing_price = this.curve_seller.price.get(0) + (this.curve_buyer.price.get(0) - this.curve_seller.price.get(0)) * this.clearing_scalar;
					}
				}
			}else if(this.clearing_quantity < this.unresponsive_buy){
				this.clearing_type = ClearingType.FAILURE;
				this.clearing_price = this.pricecap;
			}else if(this.clearing_quantity < this.unresponsive_sell){
				this.clearing_type = ClearingType.FAILURE;
				this.clearing_price = -this.pricecap;
			}else if(this.clearing_quantity == this.unresponsive_buy && this.clearing_quantity == this.unresponsive_sell){
				// only cleared unresponsive loads
				this.clearing_type = ClearingType.PRICE;
				this.clearing_price = 0.0;
			}
                
		}else{// If the market mode MD_NONE and at least one side is not given
			String missingBidder = "";
			if(this.curve_seller.count > 0 && this.curve_buyer.count == 0){
				this.clearing_price = this.curve_seller.price.get(0) - bid_offset;
			}else if(this.curve_seller.count == 0 && this.curve_buyer.count > 0){
				this.clearing_price = this.curve_buyer.price.get(0) + bid_offset;
			}else if(this.curve_seller.count > 0 && this.curve_buyer.count > 0){
				this.clearing_price = this.curve_seller.price.get(0) + (this.curve_buyer.price.get(0) - this.curve_seller.price.get(0)) * this.clearing_scalar;
			}else if(this.curve_seller.count == 0 && this.curve_buyer.count == 0){
				this.clearing_price = 0.0;
			}
			this.clearing_quantity = 0.0;
			this.clearing_type = ClearingType.NULL;
		    if(this.curve_seller.count == 0){
		    	missingBidder = "seller";
		    }else if(this.curve_buyer.count == 0){
		    	missingBidder = "buyer";
		    }
			//System.out.println("Market	" + this.name + "	fails to clear due to missing	" + missingBidder);
			bw.write("Market	" + this.name + "	fails to clear due to missing	" + missingBidder);
			bw.newLine();
			bw.flush();
		}
		//Calculation of the marginal
		double marginal_total = this.marginal_quantity = this.marginal_frac = 0.0;
        if(this.clearing_type == ClearingType.BUYER){
        	double marginal_subtotal = 0.0;
        	int i = 0;
        	for(i=0; i<this.curve_buyer.count; i++){
        		if(this.curve_buyer.price.get(i) > this.clearing_price){
        			marginal_subtotal = marginal_subtotal + this.curve_buyer.quantity.get(i);
        		}else{
        			break;
        		}
        	}
        	this.marginal_quantity = this.clearing_quantity - marginal_subtotal;
        	for(int j=i; j<this.curve_buyer.count; j++){
        		if(this.curve_buyer.price.get(i) == this.clearing_price){
        			marginal_total += this.curve_buyer.quantity.get(i);
        		}else{
        			break;
        		}
        	}
        	if(marginal_total > 0.0){
        		this.marginal_frac = this.marginal_quantity / marginal_total;
        	}
        }else if(this.clearing_type == ClearingType.SELLER){
        	double marginal_subtotal = 0.0;
            int i = 0;
        	for(i=0; i<this.curve_seller.count; i++){
        		if(this.curve_seller.price.get(i) > this.clearing_price){
        			marginal_subtotal = marginal_subtotal + this.curve_seller.quantity.get(i);
        		}else{
        			break;
        		}
        	}
            this.marginal_quantity =  this.clearing_quantity - marginal_subtotal;
        	for(int j=i; j<this.curve_seller.count; j++){
        		if(this.curve_seller.price.get(i) == this.clearing_price){
        			marginal_total += this.curve_seller.quantity.get(i);
        		}else{
        			break;
        		}
        	}
        	if(marginal_total > 0.0){
        		this.marginal_frac = this.marginal_quantity / marginal_total;
        	}
        }else{
        	this.marginal_quantity = 0.0;
        	this.marginal_frac = 0.0;
        }
		//System.out.println("##" + "clearing type " + this.clearing_type +
		//		"\nclearing quantity " + this.clearing_quantity + "\nclearing price " + this.clearing_price +
		//		"\ncurve buyer count " + this.curve_buyer.count + "\nunresponsive buy " +  this.unresponsive_buy +
		//		"\nresponsive buy " + this.responsive_buy + "\ncurve seller count " + this.curve_seller.count +
		//		"\nunresponsive sell " + this.unresponsive_sell + "\nresponsive sell " + this.responsive_sell +
		//		"\nmarginal quantity " + this.marginal_quantity + "\nmarginal frac " + this.marginal_frac +
		//		"\nlmp " + this.lmp + "\nrefload " + this.refload);
		bw.write("##	" + time_granted + "	" + tnext_clear + "	" + this.clearing_type + "	" + this.clearing_quantity + "	" + this.clearing_price + "	" 
				+ this.curve_buyer.count + "	" +  this.unresponsive_buy + "	" + this.responsive_buy + "	" 
				+ this.curve_seller.count + "	" + this.unresponsive_sell + "	" + this.responsive_sell + "	" 
				+ this.marginal_quantity + "	" + this.marginal_frac + "	" + this.lmp + "	" + this.refload);
		bw.newLine();
		bw.flush();
		bw.close();
	}

}
