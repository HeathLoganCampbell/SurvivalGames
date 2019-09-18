package games.bevs.survivalgames.commons.io;

@FunctionalInterface
public interface Callback<T>
{
	public abstract void done(T paramT);
}
