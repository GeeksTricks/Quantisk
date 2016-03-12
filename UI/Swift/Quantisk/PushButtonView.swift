//
//  PushButtonView.swift
//  Quantisk
//
//  Created by iMac on 29.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//


import UIKit

@IBDesignable

class PushButtonView: UIButton {

    /*
    // Only override drawRect: if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func drawRect(rect: CGRect) {
        // Drawing code
    }
    */
    @IBInspectable var fillColor: UIColor = UIColor.greenColor()
    @IBInspectable var lineColor: UIColor = UIColor.whiteColor()
    @IBInspectable var isAddButton: Bool = true
    
    override func drawRect(rect: CGRect) {
        var path = UIBezierPath(ovalInRect: rect)
        fillColor.setFill()
        path.fill()
        
        //set up the width and height variables
        //for the horizontal stroke
        let plusHeight: CGFloat = 3.0
        let plusWidth: CGFloat = min(bounds.width, bounds.height) * 0.6
        
        //create the path
        var plusPath = UIBezierPath()
        
        //set the path's line width to the height of the stroke
        plusPath.lineWidth = plusHeight
        
        //move the initial point of the path
        //to the start of the horizontal stroke
        plusPath.moveToPoint(CGPoint(
            x:bounds.width/2 - plusWidth/2 + 0.5,
            y:bounds.height/2 + 0.5))
        
        //add a point to the path at the end of the stroke
        plusPath.addLineToPoint(CGPoint(
            x:bounds.width/2 + plusWidth/2 + 0.5,
            y:bounds.height/2 + 0.5))
      if isAddButton{
        plusPath.moveToPoint(CGPoint(
            x:bounds.width/2  + 0.5,
            y:bounds.height/2 + plusWidth/2 + 0.5))
        
        //add a point to the path at the end of the stroke
        plusPath.addLineToPoint(CGPoint(
            x:bounds.width/2  + 0.5,
            y:bounds.height/2 - plusWidth/2 + 0.5))
        }
        //set the stroke color
        lineColor.setStroke()
        
        //draw the stroke
        plusPath.stroke()
    }
}
