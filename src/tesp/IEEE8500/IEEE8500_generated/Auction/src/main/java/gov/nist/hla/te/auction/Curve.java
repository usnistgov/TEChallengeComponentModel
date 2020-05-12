package gov.nist.hla.te.auction;

import java.util.ArrayList;

public class Curve {
	//Accumulates a set of price, quantity bids for later aggregation

    //The default order is descending by price.

    //Attributes:
    //    price: array of prices, in $/kWh
    //    quantity: array of quantities, in kW
    //    count: the number of collected bids
    //    total: the total kW bidding
    //    total_on: the total kW bidding that are currently on
    //    total_off: the total kW bidding that are currently off
	
	public ArrayList<Double> price;
	public ArrayList<Double> quantity;
	public int count;
	public double total;
	public double total_on;
	public double total_off;
	
	Curve(){
		this.price = new ArrayList<Double>();
		this.quantity = new ArrayList<Double>();
		this.count = 0;
		this.total = 0.0;
		this.total_on = 0.0;
		this.total_off = 0.0;
	}
	public void set_curve_order(String flag){
		//Set the curve order (by price) to ascending or descending

        //Args:
        //    flag: 'ascending' or 'descending'
		
		if(flag.equals("ascending")){
			ArrayList<Double> revPrice = new ArrayList<Double>();
			ArrayList<Double> revQuantity = new ArrayList<Double>();
			for (int i = this.price.size() - 1; i >= 0; i--) {
	            revPrice.add(this.price.get(i)); 
	        } 
			this.price = revPrice;
			for (int i = this.quantity.size() - 1; i >= 0; i--) {
	            revQuantity.add(this.quantity.get(i)); 
	        }
			this.quantity = revQuantity;
		}
	}
	public void add_to_curve(double price, double quantity, boolean is_on){
		//Add one point to the curve

        //Args:
        //    price: the bid price, should be $/kWhr
        //    quantity: the bid quantity, should be kW
        //    is_on: True if the load is currently on, False if not
            
		int value_insert_flag;
		if(quantity == 0.0){
			return;
		}
		this.total += quantity;
		if(is_on){
			this.total_on += quantity;
		}else{
			this.total_off += quantity;
		}
		value_insert_flag = 0;
		if(this.count == 0){
			// Since it is the first time assigning values to the curve, define an empty array for the price and mean
			this.price = new ArrayList<Double>();
			this.quantity = new ArrayList<Double>();
			this.price.add(price);
			this.quantity.add(quantity);
            this.count += 1;
		}else{
			value_insert_flag = 0;
			for(int i=0; i<this.count; i++){
				// If the price is larger than the compared curve section price, price inserted before that section of the curve
				if(price >= this.price.get(i)){
					if(i == 0){
						// If the price is larger than that of all the curve sections, insert at the beginning of the curve
						this.price.add(0, price);
						this.quantity.add(0, quantity);
					}else{
						this.price.add(i, price);
						this.quantity.add(i, quantity);
					}
					this.count += 1;
					value_insert_flag = 1;
					break;
				}
			}
			// If the price is smaller than that of all the curve sections, insert at the end of the curve
			if(value_insert_flag == 0){
				this.price.add(price);
				this.quantity.add(quantity);
				this.count += 1;
			}
			
		}
	}

}
