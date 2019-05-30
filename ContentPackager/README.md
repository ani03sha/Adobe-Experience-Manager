# Content Packager

Sometimes we are given with the details of content paths in JCR (crx/de) also called filters (the same which we use to create a package in AEM via the package manager). 

If there are multiple different paths then it becomes tedious to add each content path as a filter one by one. This utility in AEM will read the content paths in the excel file and will create a package in package manager based on that.

This utility contains a user interface (AEM Console) which lets you fill the package details like the package name, package group and an upload button to upload the excel file.


## Steps to configure

For this, you can refer my blog - [Create a package in AEM from Excel file entries](https://aem.redquark.org/2019/05/create-package-in-aem-from-excel-file.html)


## How does it work?

Here, we are creating following things -
1. Custom console in the "Tools" menu of the AEM server
2. Apache POI utility service reads the excel file and create a list containing all the content filter paths.
3. The list creates is then passed to the package creation service which then creates users one by one using **org.apache.jackrabbit.vault.packaging** API.

## Issues

If you face any issues or problems, you are welcome to open issues. You can do this by following steps - 

* Go to the Issues tab in the repository
* Click on New issue button
* Give appropriate title to the issue
* Add detailed description of the issue and if possible, steps to reproduce
* Click on Open issue button

## How to contribute

Contributions are more than welcome in this project. Below are the steps, you can follow to contribute - 

* Switch to the 'develop' branch of the repository
* Clone the develop branch in your local system
* Make your changes
* Open a pull request against the 'develop' branch only.