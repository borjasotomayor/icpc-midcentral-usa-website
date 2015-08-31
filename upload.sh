#!/bin/bash

rsync -azP ./_build/html/ linux.cs.uchicago.edu:~/html/mcpc-website/
