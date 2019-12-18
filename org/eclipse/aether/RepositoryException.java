package org.eclipse.aether;

public class RepositoryException extends Exception
{
    public RepositoryException(final String s) {
        super(s);
    }
    
    public RepositoryException(final String s, final Throwable t) {
        super(s, t);
    }
    
    protected static String getMessage(final String s, final Throwable t) {
        String string = "";
        if (t != null) {
            String s2 = t.getMessage();
            if (s2 == null || s2.length() <= 0) {
                s2 = t.getClass().getSimpleName();
            }
            string = s + s2;
        }
        return string;
    }
}
