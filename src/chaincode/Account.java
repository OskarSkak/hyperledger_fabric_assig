package chaincode;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class Account {
    @Property()
    private final String name;

    @Property()
    private final String email;

    @Property()
    private final int balance;

    @Property()
    private final String currType;

    @Property()
    private final String accType;

    public String getEmail(){ return email; }
    
    public String getName(){ return name; }
    
    public int getBalance(){ return balance; }

    public String getCurrType(){ return currType; }

    public String getAccType(){ return accType; }

    public Account(
                    @JsonProperty("name") final String name, 
                    @JsonProperty("email") final String email, 
                    @JsonProperty("balance") final int balance, 
                    @JsonProperty("currType") final String currType, 
                    @JsonProperty("accType") final String accType    
                   ){
                       this.name = name;
                       this.email = email;
                       this.balance = balance;
                       this.currType = currType;
                       this.accType = accType;
                   }

    @Override
    public boolean equals(final Object obj){
        Account o = (Account)obj; 

        return (
                (this == object) || 
                (getName().equals(o.getName()) &&
                getEmail().equals(o.getEmail()) &&
                getBalance() == o.getBalance() &&
                getCurrType().equals(o.getCurrType()) &&
                getAccType().equals(o.getAccType())
        ));
    }

    @Override
    public int hashCode() {
        return Object.hash(getName(), getEmail(), getBalance(), getCurrType(), getAccType());
    }
}
