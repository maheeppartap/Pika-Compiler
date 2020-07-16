package parseTree;

import java.util.Iterator;

public class PathToRootIterable implements Iterable<ParseNode> {
	final ParseNode startNode;
	public PathToRootIterable(ParseNode startNode) {
		this.startNode = startNode;
	}
	
	@Override
	public Iterator<ParseNode> iterator() {
		return new PathToRootIterator(startNode);
	}
	
	class PathToRootIterator implements Iterator<ParseNode> {
		ParseNode current;
		public PathToRootIterator(ParseNode node) {
			current = node;
		}

		// just a reminder for next time.
		// THIS @override TOOK YOU 5 HOURS TO REALIZE IT'S MISSING
		// I hate java....
		@Override
		public boolean hasNext() {
			return current.parent != null;
		}

		@Override
		public ParseNode next() {
			current = current.parent;
			return current;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
