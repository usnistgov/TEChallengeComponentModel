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

public class SimpleAuction{
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
		this.refload = kw;
	}
	public void set_lmp(double lmp){
		this.lmp = lmp;
	}
	public void initAuction(){
		this.clearing_price = this.lmp = this.mean;
	}
	public void update_statistics(){
		int sample_need = 0;
	}
	public void clear_bids(){
		this.curve_buyer = new Curve();
		this.curve_seller = new Curve();
		this.unresp = this.refload;
	}
	public void collect_bid(List<Object> bid){
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
		File txtfile = new File(SimpleAuction.class.getResource("/").toURI());
		BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile + "/" + "simple_auction.txt", true));
		if(this.unresp > 0){
			this.curve_buyer.add_to_curve(this.pricecap, this.unresp, true);
		}else{
			//System.out.println("## unresp < 0" + " " + this.unresp + " " + this.curve_buyer.count +
			//		" " + this.curve_buyer.total + " " + this.curve_buyer.total_on + " " + this.curve_buyer.total_off);
			bw.write("## unresp < 0 " + this.unresp + " " + this.curve_buyer.count +
					" " + this.curve_buyer.total + " " + this.curve_buyer.total_on + " " + this.curve_buyer.total_off);
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
	// aggregates the buyer curve into a quadratic or straight-line fit with zero intercept, returned as
	// [Qunresp, Qmaxresp, degree, c2, c1]
	// scaled to MW instead of kW for the opf
	public ArrayList aggregate_bid(Curve crv){
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
	
	public void clear_market() throws Exception
	{
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
			while(i < this.curve_buyer.count && j < this.curve_seller.count && this.curve_buyer.price.get(i) >= this.curve_seller.price.get(i)){
				double buy_quantity = demand_quantity +this.curve_buyer.quantity.get(i);
				double sell_quantity = supply_quantity + this.curve_seller.quantity.get(i);
				// If marginal buyer currently:
				if(buy_quantity > sell_quantity){
					this.clearing_quantity = supply_quantity = sell_quantity;
					a = b = this.curve_buyer.price.get(i);
					j += 1;
					check = 0;
					this.clearing_type = ClearingType.BUYER;
				}else if(buy_quantity < sell_quantity){// If marginal seller currently:
					this.clearing_quantity = demand_quantity = buy_quantity;
					a = b = this.curve_seller.price.get(i);
					j += 1;
					check = 0;
					this.clearing_type = ClearingType.SELLER;
				}else{// Buy quantity equal sell quantity but price split
					this.clearing_quantity = demand_quantity = supply_quantity = buy_quantity;
		            a = this.curve_buyer.price.get(i);
		            b = this.curve_seller.price.get(i);
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
				}else{// No side exausted
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
			System.out.println("Market " + this.name + " fails to clear due to missing " + missingBidder);
			bw.write("Market " + this.name + " fails to clear due to missing " + missingBidder);
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
		bw.write("##" + this.clearing_type + this.clearing_quantity + this.clearing_price + this.curve_buyer.count 
				+  this.unresponsive_buy + this.responsive_buy + this.curve_seller.count + this.unresponsive_sell 
				+ this.responsive_sell + this.marginal_quantity + this.marginal_frac + this.lmp + this.refload);
		bw.newLine();
		bw.flush();
		bw.close();
	}

}
