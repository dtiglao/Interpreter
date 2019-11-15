package JuliaScanner;

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
