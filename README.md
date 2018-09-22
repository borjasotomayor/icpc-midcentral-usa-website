This is the source code of the ACM/ICPC Mid-Central USA Regional Contest's website.

If you're looking for the actual website, please head over to https://www.icpc-midcentral.us/

Software requirements
---------------------

This website is written using [Sphinx](http://www.sphinx-doc.org). To build it,
you will need to make sure you have the necessary Python packages installed.
Just run the following:

    pip3 install -r requirements.txt

Building the website
--------------------

To build the website, just run the following:

    make html

This will build the website into the ``_build/html/`` directory.

Setting up deployment
---------------------

The website is deployed using [GitHub Pages](https://pages.github.com/). The repository
needs to be set up to publish the contents of the ``gh-pages`` branch.

If you are setting up this repository for the very first time, you need to create
the ``gh-pages`` branch as follows:

    git checkout --orphan gh-pages
    git reset --hard
    git commit --allow-empty -m "Initializing gh-pages branch"
    git push origin gh-pages
    git checkout master

The above steps only need to be carried out *once* (ever). The following steps
need to be done every time you create a new clone of the repository, and want
to be able to deploy from it:

    make clean
    git worktree add -B gh-pages _build/html origin/gh-pages

This associates the ``gh-pages`` branch with the ``_build/html`` directory. So, anything
we put there, will be pushed to the ``gh-pages`` branch.

Deploying
---------

Once you have completed the above steps, you can deploy simply by running this:

    make html
    make deploy


