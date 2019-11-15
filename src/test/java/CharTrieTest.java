import org.junit.Test;

import edu.smith.cs.csc212.speller.CharTrie;
import org.junit.Assert;
import org.junit.Test;

public class CharTrieTest {

	@Test
	public void charTrieTest() {
		CharTrie c = new CharTrie();
		c.insert("cat");
		c.insert("car");
		Assert.assertEquals(5, c.countNodes());
	}
}
