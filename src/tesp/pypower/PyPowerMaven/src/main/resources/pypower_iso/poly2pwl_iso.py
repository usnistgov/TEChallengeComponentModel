# Copyright (c) 1996-2015 PSERC. All rights reserved.
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file.

"""Converts polynomial cost variable to piecewise linear.
"""

from numpy import ones, zeros, r_

from pypower_iso.idx_cost_iso import MODEL, COST, NCOST, PW_LINEAR

from pypower_iso.totcost_iso import totcost_iso


def poly2pwl_iso(polycost, Pmin, Pmax, npts):
    """Converts polynomial cost variable to piecewise linear.

    Converts the polynomial cost variable C{polycost} into a piece-wise linear
    cost by evaluating at zero and then at C{npts} evenly spaced points between
    C{Pmin} and C{Pmax}. If C{Pmin <= 0} (such as for reactive power, where
    C{P} really means C{Q}) it just uses C{npts} evenly spaced points between
    C{Pmin} and C{Pmax}.
    """
    pwlcost = polycost
    ## size of piece being changed
    m, n = polycost.shape
    ## change cost model
    pwlcost[:, MODEL]  = PW_LINEAR * ones(m)
    ## zero out old data
    pwlcost[:, COST:COST + n] = zeros(pwlcost[:, COST:COST + n].shape)
    ## change number of data points
    pwlcost[:, NCOST]  = npts * ones(m)

    for i in range(m):
        if Pmin[i] == 0:
            step = (Pmax[i] - Pmin[i]) / (npts - 1)
            xx = range(Pmin[i], step, Pmax[i])
        elif Pmin[i] > 0:
            step = (Pmax[i] - Pmin[i]) / (npts - 2)
            xx = r_[0, range(Pmin[i], step, Pmax[i])]
        elif Pmin[i] < 0 & Pmax[i] > 0:        ## for when P really means Q
            step = (Pmax[i] - Pmin[i]) / (npts - 1)
            xx = range(Pmin[i], step, Pmax[i])
        yy = totcost_iso(polycost[i, :], xx)
        pwlcost[i,      COST:2:(COST + 2*(npts-1)    )] = xx
        pwlcost[i,  (COST+1):2:(COST + 2*(npts-1) + 1)] = yy

    return pwlcost
