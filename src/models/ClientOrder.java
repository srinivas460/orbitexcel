package models;

import java.io.Serializable;

/**
 * The Class ClientOrder.
 */
public class ClientOrder implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The selection. */
	private ClientSelection selection;

	/** The type. */
	private String type;

	/**
	 * Instantiates a new client order.
	 */
	public ClientOrder() {

	}

	/**
	 * Instantiates a new client order.
	 *
	 * @param selection the selection
	 * @param type      the type
	 */
	public ClientOrder(ClientSelection selection, String type) {
		this.selection = selection;
		this.type = type;
	}

	/**
	 * Gets the client selection.
	 *
	 * @return the client selection
	 */
	public ClientSelection getClientSelection() {
		return selection;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the client selection.
	 *
	 * @param selection the new client selection
	 */
	public void setClientSelection(ClientSelection selection) {
		this.selection = selection;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
}