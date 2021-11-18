package app; 

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class ClientApp {
    static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletPath);
		Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("AccountContract");

			byte[] result;
            //Key of choice + constructor arguments
            contract.submitTransaction("createAccount", args[0], args[1], args[2], Integer.valueOf(args[3]), args[4], args[5]);

			result = contract.evaluateTransaction("queryAccount", args[0]);
			System.out.println("Created account: " + new String(result));

			contract.submitTransaction("setBalance", args[0], Integer.valueOf(args[6]));

			result = contract.evaluateTransaction("queryAccount", args[0]);
			System.out.println("Account with changed balance: " + new String(result));
		}
	}
}
