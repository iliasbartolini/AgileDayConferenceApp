package it.aglieday.data;

import java.util.ArrayList;
import java.util.List;


public class TweetList extends ArrayList<Tweet> implements List<Tweet>  {
	private static final long serialVersionUID = 1L;
	private static TweetList empty = new TweetList(0);
	
	public TweetList(int length) {
		super(length);
	}

	public static TweetList Empty() {
		return empty;
	}
	
}
