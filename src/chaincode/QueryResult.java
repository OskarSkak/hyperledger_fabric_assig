package chaincode;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class QueryResult {
    @Property()
    private final String key;

    @Property()
    private final Account record;

    public QueryResult(@JsonProperty("Key") final String key, @JsonProperty("Record") final Account record) {
        this.key = key;
        this.record = record;
    }

    public String getKey() {
        return key;
    }

    public Account getRecord() {
        return record;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        QueryResult other = (QueryResult) obj;

        return this.getRecord().equals(other.getRecord()) && 
                this.getKey().equals(other.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getKey(), this.getRecord());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [key=" + key + ", record="
                + record + "]";
    }
}
