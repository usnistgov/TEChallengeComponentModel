#!/bin/bash

# select just the LMP_PRC data
grep "LMP_PRC" $1 > lmp_prc

# grab the one day of interest
grep "2017-07-07" lmp_prc > lmp_prc_20170707

# select the VALUE column and prefix with +300s
awk -F "\"*,\"*" '{printf "+300s,%s\n",$14}' lmp_prc_20170707 > lmp.player

# replace the first +300s with the start date
sed -i '1s/+300s/2017-07-07 00:00:00/' lmp.player

# remove temporary files
rm lmp_prc
rm lmp_prc_20170707

