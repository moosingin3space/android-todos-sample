#!/bin/bash

##
# run-static-analysis.sh
#
# Runs FB's Infer tool (http://fbinfer.com/) on the android app for pull requests only.
#
# From: https://github.com/facebook/infer/issues/153

set -ev

echo "*** TRY TO INIT INFER ***"

# 1. Install Opaml
echo "> Try to get OPAM package"
wget https://github.com/ocaml/opam/releases/download/1.2.2/opam-1.2.2-x86_64-Linux -O opam
chmod ugo+x opam
./opam init --yes --comp=4.01.0 #(then say 'y' to the final question)
eval `./opam config env`
./opam install --yes sawja.1.5.1 atdgen.1.6.0 javalib.2.3.1 extlib.1.5.4 #(then say 'y' to the question)


# Good reference at http://www.cyberciti.biz/faq/unix-linux-test-existence-of-file-in-bash/
[ -s opam ] && echo "Opam downloaded successfully." || echo "Something went wrong :("


# 2. Install Infer (latest version)
INFER_GIT_PATH="https://github.com/facebook/infer.git"
echo "> Try to Clone Infer from ${INFER_GIT_PATH}"
git clone ${INFER_GIT_PATH} vendor/infer

INFER_PATH="./vendor/infer/infer/bin/infer"
if [ -s ${INFER_PATH} ]
then
    echo "Infer downloaded successfully."

    echo "Try to Make Infer."
    cd vendor/infer/
    make -C infer java
    cd ../../

    ${INFER_PATH} -- ./gradlew --full-stacktrace assembleDebug

else
    echo "Something went wrong :("
fi

echo "*** INFER INITIALIZATION IS DONE ***"
