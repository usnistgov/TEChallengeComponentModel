# update_glm <glm_file> <csv_file>
# read a GLM file and add/replace values for objects as defined in the CSV file

import csv
import sys 

current_object_name = [] # global stack
current_object_info = [] # global stack

glm_reader = open(sys.argv[1], 'r')
glm_writer = open(sys.argv[1] + '.modified', 'w')

# used to iterate over two elements at a time
def pairwise(iterable):
    a = iter(iterable)
    return zip(a,a)

# parse CSV file into (csv_info, glm_objects)
# filename:
#   location of a CSV file where each row corresponds to a GLM object definition
#   row format: object_name,object_type,property1,value1,property2,value2,...
# csv_info:
#   dict from GLM object name to another dict that maps property -> value
# glm_objects:
#   set of GridLAB-D object types defined in the CSV file
def parse_csv(filename):
    csv_info = {}
    glm_objects = set()

    with open(filename) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            glm_objects.add(row[1])
            info = {}
            for k, v in pairwise(row[2:]):
                info[k] = v
            csv_info[row[0]] = info

    return csv_info, glm_objects

# find the GridLAB-D object name for the current scope
def find_name(reader):
    name = ''
    depth = 0
    position = reader.tell()

    while name == '':
        line = reader.readline()
        if not line:
            # end of file
            print('ERROR - corrupt GLM file')
            exit()

        lst = line.split()
        if len(lst) > 1 and lst[0] == 'object':
            # entering new object scope
            depth += 1
        elif len(lst) > 0 and lst[0] == '}':
            # leaving object scope
            if depth == 0:
                print('ERROR - corrupt GLM file')
                exit()
            depth -= 1
        elif len(lst) > 1 and lst[0] == 'name' and depth == 0:
            # found object name
            name = lst[1].strip(';')

    reader.seek(position)
    return name

# process one line from the GLM file
# assumes the file has the following format (the same as prep_substation.py):
#   for new object definitions, the line must start with `object <object_type>`
#   the closing brace } must be the first non-whitespace character on its line
#   for object properties, the line must start with `<property_name> <value>`
def process_line(line):
    lst = line.split()

    if len(lst) > 1 and lst[0] == 'object' and lst[1] in glm_objects:
        # new object of potential interest
        name = find_name(glm_reader)
        if name in csv_info.keys():
            print(name)
        glm_writer.write(line)
    elif len(lst) > 0 and lst[0] == '}':
        # end object definition
        glm_writer.write(line)
    elif len(lst) > 1 and len(current_object_name) > 0:
        # potential object property of interest
        glm_writer.write(line)
    else:
        glm_writer.write(line)

# read CSV file
csv_info, glm_objects = parse_csv(sys.argv[2])

# read GLM file
while True:
    line = glm_reader.readline()
    if not line:
        break
    process_line(line)
glm_reader.close()
glm_writer.close()

