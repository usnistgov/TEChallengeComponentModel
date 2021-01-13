#!/bin/bash
python3 -c 'import prep_substation; prep_substation.prep_substation("IEEE_8500")'

cp IEEE_8500_agent_dict.json agent_dict.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.028891/' agent_dict.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.059717/' agent_dict.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict.json

cp IEEE_8500_agent_dict.json agent_dict_tou.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.10512/' agent_dict_tou.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.015045/' agent_dict_tou.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict_tou.json

cp IEEE_8500_agent_dict.json agent_dict_flat.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.102/' agent_dict_flat.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.0/' agent_dict_flat.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict_flat.json
