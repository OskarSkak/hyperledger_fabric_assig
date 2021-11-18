package app;

import java.nio.file.Paths;
import java.util.Properties;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class EnrollAdmin {
	static { System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true"); }

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put("pemFile", "../../test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
		caClient.setCryptoSuite(CryptoSuiteFactory.getDefault().getCryptoSuite());
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
		if (wallet.get("admin") != null) return;
		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		enrollmentRequestTLS.addHost("localhost");
		enrollmentRequestTLS.setProfile("tls");
		wallet.put("admin", Identities.newX509Identity("Org1MSP", caClient.enroll("admin", "adminpw", enrollmentRequestTLS)));
	}
}
