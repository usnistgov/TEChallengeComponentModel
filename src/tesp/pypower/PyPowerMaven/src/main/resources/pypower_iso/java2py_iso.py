import pickle
import json
import numpy as np


def java2py_iso(java_obj):
    """converts json string to python dict and returns it
    """
    py_obj = json.loads(java_obj)
    if 'bus' in py_obj.keys():
        py_obj['bus'] = np.array (py_obj['bus'])
    if 'gen' in py_obj.keys():
        py_obj['gen'] = np.array (py_obj['gen'])
    if 'branch' in py_obj.keys():
        py_obj['branch'] = np.array (py_obj['branch'])
    if 'areas' in py_obj.keys():
        py_obj['areas'] = np.array (py_obj['areas'])
    if 'gencost' in py_obj.keys():
        py_obj['gencost'] = np.array (py_obj['gencost'])
    if 'FNCS' in py_obj.keys():
        py_obj['FNCS'] = np.array (py_obj['FNCS'])
    if 'UnitsOut' in py_obj.keys():
        py_obj['UnitsOut'] = np.array (py_obj['UnitsOut'])
    if 'BranchesOut' in py_obj.keys():
        py_obj['BranchesOut'] = np.array (py_obj['BranchesOut'])
    
    return py_obj

def py2java_iso(py_obj):
    """converts python dict to json string and returns it
    """ 
    if 'om' in py_obj:
        del py_obj['om']#<cass 'pypower_iso.opf_model_iso.opf_model_iso'> is not JSON serializable

#convert all ndarrays to lists    
    for key in py_obj.keys():
        sub = py_obj[key]
        if isinstance(sub, np.ndarray):
            py_obj[key] = sub.tolist()
        elif isinstance(sub, dict):
            for key1 in sub.keys():
                sub_sub = sub[key1]
                if isinstance(sub_sub, np.ndarray):
                    sub[key1] = sub_sub.tolist()
                elif isinstance(sub_sub, dict):
                    for key2 in sub_sub.keys():
                        sub_sub_sub = sub_sub[key2]
                        if isinstance(sub_sub_sub, np.ndarray):
                            sub_sub[key2] = sub_sub_sub.tolist()
                        elif isinstance(sub_sub_sub, dict):
                            for key3 in sub_sub_sub.keys():
                                sub_sub_sub_sub = sub_sub_sub[key3]
                                if isinstance(sub_sub_sub_sub, np.ndarray):
                                    sub_sub_sub[key3] = sub_sub_sub_sub.tolist()
                                elif isinstance(sub_sub_sub, dict):
                                    print('shit')
                                    
           
    java_obj = json.dumps(py_obj)
    
    return java_obj


if __name__ == '__main__':
#    print(os.path.dirname(os.path.realpath(__file__)))
#    print(os.getcwd())
    f = open('json.pckl', 'rb')
    java_obj = pickle.load(f)
    f.close()
    py_obj = java2py_iso(java_obj)
    
    f = open('dict.pckl', 'rb')
    py_obj = pickle.load(f)
    f.close()
    java_obj = py2java_iso(py_obj)