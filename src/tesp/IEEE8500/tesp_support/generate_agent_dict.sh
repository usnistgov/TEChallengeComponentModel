#!/bin/bash
if [ $# -ne 1 ]; then
    echo "usage: $0 <model_name>"
    exit 1
fi

python3 -c "import prep_substation; prep_substation.prep_substation(\"$1\")"

cp $1_agent_dict.json agent_dict_rtp.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.028891/' agent_dict_rtp.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.059717/' agent_dict_rtp.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict_rtp.json
sed -i -r 's/mhse/mtr/g' agent_dict_rtp.json # fix for FeederGenerator where the grandparent node is the correct meter to use

cp $1_agent_dict.json agent_dict_tou.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.10512/' agent_dict_tou.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.015045/' agent_dict_tou.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict_tou.json
sed -i -r 's/mhse/mtr/g' agent_dict_tou.json # fix for FeederGenerator where the grandparent node is the correct meter to use

cp $1_agent_dict.json agent_dict_flat.json
sed -i -r 's/"init_price": [^,]+/"init_price": 0.102/' agent_dict_flat.json
sed -i -r 's/"init_stdev": [^,]+/"init_stdev": 0.0/' agent_dict_flat.json
sed -i -r 's/"max_capacity_reference_bid_quantity": [^,]+/"max_capacity_reference_bid_quantity": 50000/' agent_dict_flat.json
sed -i -r 's/mhse/mtr/g' agent_dict_flat.json # fix for FeederGenerator where the grandparent node is the correct meter to use
