# Copyright (c) 1996-2015 PSERC. All rights reserved.
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file.

"""Solves a DC optimal power flow.
"""

from pypower_iso.opf_args_iso import opf_args2_iso
from pypower_iso.ppoption_iso import ppoption_iso
from pypower_iso.opf_iso import opf_iso


def dcopf_iso(*args, **kw_args):
    """Solves a DC optimal power flow.

    This is a simple wrapper function around L{opf} that sets the C{PF_DC}
    option to C{True} before calling L{opf}.
    See L{opf} for the details of input and output arguments.

    @see: L{rundcopf}

    @author: Ray Zimmerman (PSERC Cornell)
    """
    ppc, ppopt = opf_args2_iso(*args, **kw_args);
    ppopt = ppoption_iso(ppopt, PF_DC=1)

    return opf_iso(ppc, ppopt)
