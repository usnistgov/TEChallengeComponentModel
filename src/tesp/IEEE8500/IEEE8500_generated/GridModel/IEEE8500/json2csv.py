# convert the metrics output to multiple CSV files
# based on the PNNL process_glm.py script
# 
# usage:
#	python3 glm_dict.py <glm_file (without .glm)>
#	python3 json2csv.py <glm_file (without .glm)>
import json;
import sys;

def output_csv(object_type, object_names, times):
	# read the JSON output for provided object type
	infile = open(object_type + '_' + sys.argv[1] + '_metrics.json').read()
	infile_content = json.loads(infile)

	# remove the metadata
	infile_content.pop('StartTime')
	metadata = infile_content.pop('Metadata')

	print('\nThere are', len(object_names), object_type, 'objects that will be recorded across', len(metadata.items()), 'CSV files')

	# output a CSV file for each property
	for key, val in metadata.items():
		index = val['index']
		filename = object_type + '_' + key + '.csv'
		csv_file = open(filename, 'w')

		# print the CSV header row
		header = 'time'
		for name in object_names:
			header = header + ',' + name
		print(header, file=csv_file)

		# print the next row
		for t in times:
			data = str(t)
			for name in object_names:
				# content[time][object_name][property_index]]
				data = data + ',' + str(infile_content[str(t)][name][index])
			print(data, file=csv_file)
		csv_file.close()
		print("\t", filename)

# read a dictionary of all the monitored GridLAB-D objects
lp = open(sys.argv[1] + '_glm_dict.json').read()
glm_dict = json.loads(lp)
sub_keys = list(glm_dict['feeders'].keys())
sub_keys.sort()
house_keys = list(glm_dict['houses'].keys())
house_keys.sort()
meter_keys = list(glm_dict['billingmeters'].keys())
meter_keys.sort()
inv_keys = list(glm_dict['inverters'].keys())
inv_keys.sort()
cap_keys = list(glm_dict['capacitors'].keys())
cap_keys.sort()
reg_keys = list(glm_dict['regulators'].keys())
reg_keys.sort()
xfMVA = glm_dict['transformer_MVA']
matBus = glm_dict['matpower_id']
print ("File", sys.argv[1], "has substation", sub_keys[0], "at Matpower bus", matBus, "with", xfMVA, "MVA transformer")

# parse the substation metrics file first; there should just be one entity per time sample
sub_file = open ("substation_" + sys.argv[1] + "_metrics.json").read()
sub_file_content = json.loads(sub_file)

# make a sorted list of the sample times in hours
# each metrics file should have matching time points
print ("\nMetrics data starting", sub_file_content['StartTime'])
sub_file_content.pop('StartTime')
sub_metadata = sub_file_content.pop('Metadata')
times = list(map(int,list(sub_file_content.keys())))
times.sort()
print ("There are", len (times), "sample times at", times[1] - times[0], "second intervals")

# created a sorted array of the property names
sub_header = [0] * len(sub_metadata)
for key, val in sub_metadata.items():
	sub_header[val['index']] = key

# output a CSV file with all the substation properties
sub_csv_file = open('substation.csv', 'w')

# print the CSV header row
row = 'time'
for val in sub_header:\
	row = row + ',' + val
print(row, file=sub_csv_file)

# print the next row
for t in times:
	row = str(t)
	for i in range(len(sub_header)):
		row = row + ',' + str(sub_file_content[str(t)][sub_keys[0]][i])
	print(row, file=sub_csv_file)
sub_csv_file.close()

output_csv('house', house_keys, times)
output_csv('billing_meter', meter_keys, times)
output_csv('inverter', inv_keys, times)
output_csv('capacitor', cap_keys, times)
output_csv('regulator', reg_keys, times)
