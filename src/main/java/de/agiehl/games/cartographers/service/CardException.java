package de.agiehl.games.cartographers.service;

public class CardException extends RuntimeException {

    private static final long serialVersionUID = 3182660394354196182L;

    public CardException(String msg, Exception e) {
        super(msg, e);
	}

	public CardException(String msg) {
        super(msg);
	}

}
