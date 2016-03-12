//
//  GTGrapViewController.swift
//  Quantisk
//
//  Created by iMac on 25.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit

class GTGrapViewController: UIViewController,statView {
    var typeStatic:Int = 0
    var siteID:Int = 0
    @IBOutlet weak var medalView: MedalView!

    //Counter outlets
    @IBOutlet weak var counterView: CounterView!
    @IBOutlet weak var counterLabel: UILabel!
    
    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var graphView: GraphView!
    var isGraphViewShowing = false
    
    //Label outlets
    @IBOutlet weak var averageWaterDrunk: UILabel!
    @IBOutlet weak var maxLabel: UILabel!
    
    var perosnId:Int = 0
    var startDate: String = ""
    var endDate: String = ""
    
    var personsName: [String] = [""]
    var personRate: [String] = [""]
    
    func checkTotal() {
        if counterView.counter >= 8 {
            medalView.showMedal(true)
        } else {
            medalView.showMedal(false)
        }
    }
    
    func setupGraphDisplay() {
        
        //Use 7 days for graph - can use any number,
        //but labels and sample data are set up for 7 days
        let noOfDays:Int = 7
        
        //1 - replace last day with today's actual data
        graphView.graphPoints[graphView.graphPoints.count-1] = counterView.counter
        
        //2 - indicate that the graph needs to be redrawn
        graphView.setNeedsDisplay()
        
        maxLabel.text = "\(graphView.graphPoints.maxElement()!)"
        
        //3 - calculate average from graphPoints
        let average = graphView.graphPoints.reduce(0, combine: +) / graphView.graphPoints.count
        averageWaterDrunk.text = "\(average)"
        
        //set up labels
        //day of week labels are set up in storyboard with tags
        //today is last day of the array need to go backwards
        
        //4 - get today's day number
        let dateFormatter = NSDateFormatter()
        let calendar = NSCalendar.currentCalendar()
        let componentOptions:NSCalendarUnit = .Weekday
        let components = calendar.components(componentOptions,
            fromDate: NSDate())
        var weekday = components.weekday
        
        let days = ["S", "S", "M", "T", "W", "T", "F"]
        
        //5 - set up the day name labels with correct day
        for i in (1...days.count).reverse() {
            if let labelView = graphView.viewWithTag(i) as? UILabel {
                if weekday == 7 {
                    weekday = 0
                }
                labelView.text = days[weekday--]
                if weekday < 0 {
                    weekday = days.count - 1
                }
            }
        }
    }
    
    @IBAction func btnPushButton(button: PushButtonView) {
        if button.isAddButton {
            counterView.counter++
        } else {
            if counterView.counter > 0 {
                counterView.counter--
            }
        }
        counterLabel.text = String(counterView.counter)
     
        if isGraphViewShowing {
            counterViewTap(nil)
        }
        checkTotal()
    }
    
    @IBAction func counterViewTap(gesture:UITapGestureRecognizer?) {
        if (isGraphViewShowing) {
            
            //hide Graph
            UIView.transitionFromView(graphView,
                toView: counterView,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromLeft , UIViewAnimationOptions.ShowHideTransitionViews],
                completion:nil)
        } else {
            setupGraphDisplay()
            //show Graph
            UIView.transitionFromView(counterView,
                toView: graphView,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromRight , UIViewAnimationOptions.ShowHideTransitionViews],
                completion: nil)
        }
        isGraphViewShowing = !isGraphViewShowing
    }
    
    
        
        //Write your code in drawRect
       func drawX() {
            let myBezier = UIBezierPath()
            myBezier.moveToPoint(CGPoint(x: 20, y: 100))
            myBezier.addLineToPoint(CGPoint(x: 20, y: 200))
            myBezier.closePath()
           var color = UIColor.blackColor()   //returns color
           color.setStroke()                  // setStroke on color
            myBezier.stroke()
        }
        
        

    
    


func methodOfReceivedNotification(notification: NSNotification){
    self.personsName =  GTDBManager.sharedInstance.getTotalStatText()
    self.personRate =  GTDBManager.sharedInstance.getTotalStatSubText(self.typeStatic)
    
    print("hello")
  //  self.userNameLabel.text = personsName[0]
    
    NSNotificationCenter.defaultCenter().removeObserver(self, name: "NotificationIdentifierStat", object: nil)
    
 //   self.drawX()
    
}


override func viewDidLoad() {
    counterLabel.text = String(counterView.counter)
    
    super.viewDidLoad()
    if self.typeStatic == 1 {
        GTDBManager.sharedInstance.getLoadlStat(self.siteID)
    } else {
        GTDBManager.sharedInstance.getLoadlStatPersonId(self.siteID, personId: self.perosnId, startDate: self.startDate, endDate: self.endDate)
        print("start date = \(self.startDate) end date = \(self.endDate)")
    }
    
    NSNotificationCenter.defaultCenter().addObserver(self, selector: "methodOfReceivedNotification:", name:"NotificationIdentifierStat", object: nil)
    
    checkTotal()
    // Do any additional setup after loading the view, typically from a nib.
}

}