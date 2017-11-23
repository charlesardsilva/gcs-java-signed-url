It is a project that implements "Signed URL" for [Google Cloud Storage][gcs].

[gcs]: https://cloud.google.com/storage/


# Current status

It is the first version. Here there are units test that runs to send and get files to GCS using Signed URL.


# Installing

The project was built using the minimal thirdy library.
Execute *mvn clean install -DskipTests* 
(skip because you need to change some informations).


# How to Use

## Execute Main Tests
There is a Unit Test that runs the follow main tests, in [this class][mainTest]:

[mainTest]: https://github.com/charlesasilva61/gcs-java-signed-url/blob/master/src/test/java/com/charlesasilva61/gcsjavasignedurl/storage/StorageSignURLTest.java

**You need to change:**
- You need to get a "p12 key file", this file will be used to access the GCS, for download follow like as describe [here][gcs-authentication].
[gcs-authentication]:https://cloud.google.com/storage/docs/authentication
- Put the "p12 key file" in this path: *src/test/resources*, change the name to: *my-pk.p12*.
- For execute the success tests, you need to change the information that are in the constants:

    VALID_BUCKET = "my-bucket"; // The name of the bucket
    
    VALID_GOOGLE_ACCOUNT_ID = "my-account";  // The account that has access on GCS
    
    VALID_PASSOWRD = "validPassowrd"; // The password of p12 file.

**Tests:**

     - uploadIfAllIsOkReturnSuccessCode
This test send a file to GCS and return a Success Code. 

    - downloadIfAllIsOkReturnSuccessCode
This test get a file from GCS and return a Success Code.

    - operationWithInvalidFileThowException
   This test check if the file is invalid, in this case, throw Exception.



Now, you can execute the test.

# Versioning

gcsfuse version numbers are assigned according to [Semantic
Versioning][semver]. Note that the current major version is `0`, which means
that we reserve the right to make backwards-incompatible changes.

[semver]: http://semver.org/




