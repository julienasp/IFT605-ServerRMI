package core;

import java.io.Serializable;

public class AdminToken  implements Serializable {
	private String secretPrivateKey;

	public AdminToken(String secretPrivateKey) {
		super();
		this.secretPrivateKey = secretPrivateKey;
	}

	public String getSecretPrivateKey() {
		return secretPrivateKey;
	}

	public void setSecretPrivateKey(String secretPrivateKey) {
		this.secretPrivateKey = secretPrivateKey;
	}
	
	public boolean isPrivateKeyOK(AdminToken at){		
		return secretPrivateKey.equals(at.getSecretPrivateKey());
	}
	
}
