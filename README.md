# TEChallengeComponentModel
Component model for NIST Transactive Energy Challenge

GridLAB-D site - https://sourceforge.net/projects/gridlab-d/
GridLABD Code: svn checkout svn://svn.code.sf.net/p/gridlab-d/code/trunk gridlab-d-code

Fetch gridlab-d outputs from simulation: 
https://s3.amazonaws.com/nist-sgcps/development/TEChallengeComponentModel/NIST_TE_Model_outputs.zip

Link to Vulcanforge test UCEF server: teserver.isis.vanderbilt.edu


Building gridlab-d on linux platform:

    sudo apt-get install automake autoconf libtool curl subversion build-essential libxerces-c-dev  cmake -y 

    ############ MOST IMPORTANT 
    # Most Important Note!!!!!: We need to stick with commit 5307.....
    cd $HOME/Downloads/
    sudo svn co -q svn://svn.code.sf.net/p/gridlab-d/code/trunk@5307 gridlab-d-code
    
    cd $HOME/Downloads/gridlab-d-code
    sudo mkdir -p /usr/local/gridlab-d
    sudo autoreconf -isf
    sudo ./configure --prefix=/usr/local/gridlab-d --enable-silent-rules
    sudo make && sudo make install 
}
