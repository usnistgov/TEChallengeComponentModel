# Copyright (c) 1996-2015 PSERC. All rights reserved.
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file.

"""Runs a DC power flow.
"""

from os.path import dirname, join

from pypower_iso.ppoption_iso import ppoption_iso
from pypower_iso.runpf_iso import runpf_iso


def rundcpf_iso(casedata=None, ppopt=None, fname='', solvedcase=''):
    """Runs a DC power flow.

    @see: L{runpf}

    @author: Ray Zimmerman (PSERC Cornell)
    """
    ## default arguments
    if casedata is None:
        casedata = join(dirname(__file__), 'case9')
    ppopt = ppoption_iso(ppopt, PF_DC=True)

    return runpf_iso(casedata, ppopt, fname, solvedcase)
