package action_driven;

import remixlab.bias.*;
import remixlab.bias.event.*;
import processing.core.*;

/**
 * Created by pierre on 12/22/16.
 */
public class Ellipse extends GrabberObject {
  public float radiusX, radiusY;
  public PApplet parent;
  public PVector center;
  public int colour;
  public int contourColour;
  public int sWeight;
  public boolean move;

  public Ellipse(PApplet p, InputHandler handler) {
    super(handler);
    parent = p;
    setMouseDragBindings();
    setColor();
    setPosition();
    sWeight = 4;
    contourColour = parent.color(0, 0, 0);
  }

  public Ellipse(PApplet p, InputHandler handler, PVector c, float r) {
    super(handler);
    parent = p;
    setMouseDragBindings();
    radiusX = r;
    radiusY = r;
    center = c;
    setColor();
    sWeight = 4;
  }

  public void setColor(int myC) {
    colour = myC;
  }

  public void setColor() {
    setColor(parent.color(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255), parent.random(100, 200)));
  }

  public void setPosition(DOF2Event event) {
    setPositionAndRadii(new PVector(event.x(), event.y()), radiusX, radiusY);
  }

  public void setShape(DOF1Event event) {
    radiusX += event.dx();
    radiusY += event.dx();
  }

  public void setShape(DOF2Event event) {
    radiusX += event.dx();
    radiusY += event.dy();
  }

  public void setPositionAndRadii(PVector p, float rx, float ry) {
    center = p;
    radiusX = rx;
    radiusY = ry;
  }

  public void setPosition() {
    float maxRadius = 50;
    float low = maxRadius;
    float highX = 800 - maxRadius;
    float highY = 800 - maxRadius;
    float r = parent.random(20, maxRadius);
    setPositionAndRadii(new PVector(parent.random(low, highX), parent.random(low, highY)), r, r);
  }

  public void draw() {
    draw(colour);
  }

  public void draw(int c) {
    parent.pushStyle();
    parent.stroke(contourColour);
    parent.strokeWeight(sWeight);
    parent.fill(c);
    parent.ellipse(center.x, center.y, 2*radiusX, 2*radiusY);
    parent.popStyle();
  }

  public void setMouseDragBindings() {
    move = false;
  }

  public void setMouseMoveBindings() {
    move = true;
  }

  @Override
  public void performInteraction(DOF2Event event) {
    if (move) {
      if (event.shortcut().matches(new Shortcut(remixlab.bias.Event.NO_ID)))
        setPosition(event);
    } else {
      if (event.shortcut().matches(new Shortcut(PApplet.LEFT)))
        setPosition(event);
    }
    if (event.shortcut().matches(new Shortcut(PApplet.RIGHT)))
      setShape(event);
  }

  @Override
  public void performInteraction(DOF1Event event) {
    if (event.shortcut().matches(new Shortcut(remixlab.bias.Event.CTRL, processing.event.MouseEvent.WHEEL)))
      setShape(event);
  }

  @Override
  public void performInteraction(ClickEvent event) {
    if (event.shortcut().matches(new ClickShortcut(PApplet.LEFT, 1)))
      setColor();
  }

  @Override
  public boolean checkIfGrabsInput(DOF2Event event) {
    return checkIfGrabsInput(event.x(), event.y());
  }

  @Override
  public boolean checkIfGrabsInput(ClickEvent event) {
    return checkIfGrabsInput(event.x(), event.y());
  }

  public boolean checkIfGrabsInput(float x, float y) {
    return(parent.pow((x - center.x), 2)/parent.pow(radiusX, 2) + parent.pow((y - center.y), 2)/parent.pow(radiusY, 2) <= 1);
  }
}