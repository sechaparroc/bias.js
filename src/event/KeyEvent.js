/**************************************************************************************
 * bias_tree
 * Copyright (c) 2014-2017 National University of Colombia, https://github.com/remixlab
 * @author Jean Pierre Charalambos, http://otrolado.info/
 *
 * All rights reserved. Library that eases the creation of interactive
 * scenes, released under the terms of the GNU Public License v3.0
 * which is available at http://www.gnu.org/licenses/gpl.html
 **************************************************************************************/

import Event, { NO_ID, NO_MODIFIER_MASK } from '../Event';
import KeyShortcut from './KeyShortcut';

/**
 * A key-event is an {@link Event} specialization that
 * encapsulates a {@link KeyShortcut}. Key shortcuts may be
 * of one form out of two: 1. A single Character; or, 2. A modifier mask (such as: (ALT |
 * SHIFT)) plus a virtual-key.
 * <p>
 * <b>Note</b> that virtual key codes are used to report which keys have been
 * pressed, rather than a character generated by the combination of one or more keystrokes
 * (such as "A", which comes from shift and "a"). Their values depend on the platform your
 * running your code. In Java, for instance, have a look at
 * <a href= "http://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html">
 * KeyEvent</a> to get some VK_* values. Note that Proscene sets them automatically from
 * the platform where the framework is running.
 */
export default class KeyEvent extends Event {

  /**
   * Constructs a keyevent with <b>c</b> defining its
   * {@link KeyShortcut}.
   */
  constructor({ key = '\0', modifiers = NO_MODIFIER_MASK, virtualKey = NO_ID, other = null } = {}) {
    if ( other !== null ){
      super({ other });
      this._key = other._key;
    } else {
      super({ modifiers, id: virtualKey });
      this._key = key;
    }
  }


  get() {
    return new KeyEvent({ other: this });
  }

  shortcut() {
    if (this._key === '\0') {
      return new KeyShortcut({ modifiers: this.modifiers(), id: this.id() });
    }
    return new KeyShortcut({key: this.key()});
  }

  key() {
    return this._key;
  }
}
