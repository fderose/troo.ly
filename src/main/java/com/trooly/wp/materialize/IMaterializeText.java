package com.trooly.wp.materialize;

import java.io.IOException;

/**
 * Interface defined so that different classes can be injected to materialize text from various sources.
 */
public interface IMaterializeText {
  String getText() throws IOException;
}
