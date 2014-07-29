import remixlab.bias.core.*;
import remixlab.bias.event.*;
import remixlab.bias.agent.*;
import remixlab.bias.agent.profile.*;

import processing.core.*;

public class MOUSEAgent extends ActionMotionAgent<MotionProfile<MotionAction>, ClickProfile<ClickAction>> {
  DOF2Event event, prevEvent;
  public MOUSEAgent(InputHandler scn, String n) {
    super(new MotionProfile<MotionAction>(), new ClickProfile<ClickAction>(), scn, n);
    //default bindings
    clickProfile().setBinding(PApplet.LEFT, 1, ClickAction.CHANGE_COLOR);
    clickProfile().setBinding(DOF2Event.META, PApplet.RIGHT, 1, ClickAction.CHANGE_STROKE_WEIGHT);
    clickProfile().setBinding((DOF2Event.META | DOF2Event.SHIFT), PApplet.RIGHT, 1, ClickAction.CHANGE_STROKE_WEIGHT);
    profile().setBinding(PApplet.LEFT, MotionAction.CHANGE_POSITION);
    profile().setBinding(DOF2Event.SHIFT, PApplet.LEFT, MotionAction.CHANGE_SHAPE);
    profile().setBinding(DOF2Event.META, PApplet.RIGHT, MotionAction.CHANGE_SHAPE);
  }
  
  public void mouseEvent(processing.event.MouseEvent e) {      
    if ( e.getAction() == processing.event.MouseEvent.MOVE ) {
      event = new DOF2Event(prevEvent, e.getX(), e.getY(),e.getModifiers(), e.getButton());
      updateTrackedGrabber(event);
      prevEvent = event.get();
    }
    if ( e.getAction() == processing.event.MouseEvent.DRAG ) {
      event = new DOF2Event(prevEvent, e.getX(), e.getY(), e.getModifiers(), e.getButton());
      handle(event);
      prevEvent = event.get();
    }
    if ( e.getAction() == processing.event.MouseEvent.CLICK ) {
      handle(new ClickEvent(e.getX(), e.getY(), e.getModifiers(), e.getButton(), e.getCount()));
    }
  }
}
