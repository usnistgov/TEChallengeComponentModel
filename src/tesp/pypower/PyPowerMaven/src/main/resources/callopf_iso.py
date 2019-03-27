# Copyright (c) 1996-2015 PSERC. All rights reserved.
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file.

"""Runs an optimal power flow.
"""

from sys import stdout, stderr
import sys
#import pickle


#import os

from os.path import dirname, join

import pypower_iso.api as pp
from pypower_iso.ppoption_iso import ppoption_iso
from pypower_iso.opf_iso import opf_iso
from pypower_iso.printpf_iso import printpf_iso
from pypower_iso.savecase_iso import savecase_iso
from pypower_iso.java2py_iso import java2py_iso
from pypower_iso.java2py_iso import py2java_iso


def runopf_iso(casedata=None, ppopt=None, fname='', solvedcase=''):
    """Runs an optimal power flow.

    @see: L{rundcopf}, L{runuopf}

    @author: Ray Zimmerman (PSERC Cornell)
    """
    ## default arguments
    if casedata is None:
        casedata = join(dirname(__file__), 'case9')
    ppopt = ppoption_iso(ppopt)

    ##-----  run the optimal power flow  -----
    r = opf_iso(casedata, ppopt)

    ##-----  output results  -----
    if fname:
        fd = None
        try:
            fd = open(fname, "a")
        except IOError as detail:
            stderr.write("Error opening %s: %s.\n" % (fname, detail))
        finally:
            if fd is not None:
                printpf_iso(r, fd, ppopt)
                fd.close()

    else:
        printpf_iso(r, stdout, ppopt)

    ## save solved case
    if solvedcase:
        savecase_iso(solvedcase, r)

    return r


if __name__ == '__main__':
    ppopt = pp.ppoption_iso(VERBOSE=int(sys.argv[1]), OUT_ALL=int(sys.argv[2]), PF_DC=int(sys.argv[3]))
    ppc = java2py_iso(sys.argv[4])
    r = runopf_iso(ppc, ppopt)
    res = py2java_iso(r)
    print(res)