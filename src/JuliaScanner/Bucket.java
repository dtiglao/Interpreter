package JuliaScanner;

/** Rosny Colas and Darius Tiglao
 * Class: Bucket
 * Bucket creates a named object that is essentially just a ArrayList of type TokenRecord[].
 * Class was created so that the ArrayList would have a name and would be able to be accessed across the multiple classes.
 * Each line from the input file will be separated into an array of TokenRecords. These lines are stored in the bucket.
 * Accessing each index of the bucket, one after the other, means accessing/processing each line, one after the other.
 */

import java.util.ArrayList;

class Bucket {
    ArrayList<TokenRecord[]> bucket;

    Bucket() {
        this.bucket = new ArrayList<>();
    }

    TokenRecord[] grab(int i) {
        return this.bucket.get(i);
    }

    boolean contains(TokenRecord[] t) {
        return this.bucket.contains(t);
    }

    void add(TokenRecord[] t) {
        this.bucket.add(t);
    }

    ArrayList<TokenRecord[]> getBucket() {
        return this.bucket;
    }

    int size() {
        return this.bucket.size();
    }
}
