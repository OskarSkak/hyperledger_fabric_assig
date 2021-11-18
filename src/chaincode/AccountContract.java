package chaincode;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

@Contract(
    name = "AccountContract", 
    info = @Info(
        title = "Account Contract",
        license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "f.carr@example.com",
                        name = "F Carr",
                        url = "https://hyperledger.example.com")
    )
)

@Default
public final class AccountContract implements ContractInterface {
    private final Genson gen = new Genson();

    private enum ERRORS {
        NOT_FOUND,
        ACCOUNT_ALREADY_EXISTS
    }

    @Transaction()
    public Account queryAccount(final Context ctx, final String key){
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(key);

        if(state.isEmpty()) throw new ChainCodeException("Account does not exist", ERRORS.NOT_FOUND.toString());

        return genson.deserialize(state, Account.class);
    }

    @Transaction()
    public Account createAccount(final Context ctx, final String key,
                            final String email, final String name, 
                            final String accType, final String currType, 
                            final int balance){
        ChaincodeStub stub = ctx.getStub();

        String state = stub.getStringState(key);

        if(state.isEmpty()) throw new ChainCodeException("Account already exists", ERRORS.ACCOUNT_ALREADY_EXISTS.toString());

        Account acc = new Account(name, email, balance, currType, accType);
        state = genson.serialize(acc);
        stub.putStringState(key, state);

        return acc;
    }

    @Transaction()
    public Account setBalance(final Context ctx, final int newBal, final String key){
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(key);

        Account acc = genson.deserialize(state, Account.class);

        Account newAcc = new Account(acc.getName(), acc.getEmail(), newBal, acc.getCurrType(), acc.getAccType());
        String newState = genson.serialize(newAcc);
        return newAcc;
    }
}