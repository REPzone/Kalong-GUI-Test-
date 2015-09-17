package core;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by biospore on 9/17/15.
 */
public interface ILocalData extends Serializable{
    void addEntry(IEntry entry);
    IEntry getEntry(long id);
    void updateEntry(long id);
    void deleteEntry(long id);
    boolean containEntry(long id);
    Collection<IEntry> getAllEntries();
    void mergeLists(TreeMap<Long, IEntry> list);
}
