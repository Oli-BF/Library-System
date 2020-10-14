/**
 * 
 */
package client.controllers;

import client.Client;

/**
 * @author gaofei
 *
 */
public class ManagementController 
{
	public boolean verification;
    
    private Client client;
    
    public void setClient(Client client)
    {
        this.client = client;
    }
    
    public boolean getVerification()
    {
        return this.verification;
    }
}