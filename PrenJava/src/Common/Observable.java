package Common;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T>
{
  private final List<IObserver<T>> observers = new ArrayList<IObserver<T>>();

  public void addObserver(IObserver<T> observer)
  {
    if (!observers.contains(observer))
      observers.add(observer);
  }

  public void deleteObserver(IObserver<?> observer)
  {
    observers.remove(observer);
  }

  public void notifyObservers(T arg)
  {
    for ( IObserver<T> observer : observers )
      observer.update(arg);
  }
}