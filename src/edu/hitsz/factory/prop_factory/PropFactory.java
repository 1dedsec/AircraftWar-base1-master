package edu.hitsz.factory.prop_factory;


import edu.hitsz.prop.AbstractProp;

public interface PropFactory {
    public AbstractProp createProp(int locationX,int locationY,int speedX,int speedY);
}
