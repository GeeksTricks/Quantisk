//
//  GTStaticViewContrlor.swift
//  Quantisk
//
//  Created by iMac on 09.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit
protocol statView {
    var typeStatic:Int { get  set}
    var siteID:Int { get set}
    var perosnId:Int { get set}
    var startDate: String { get set}
    var endDate: String { get set}
    
    var personsName: [String] { get set}
    var personRate: [String] { get set}
    
    
  
}

class GTStaticViewController: UIViewController,UITableViewDataSource, UITableViewDelegate,statView {
    var typeStatic:Int = 0
    var siteID:Int = 0
    var perosnId:Int = 0
    var startDate: String = ""
    var endDate: String = ""
    
    var personsName: [String] = [""]
    var personRate: [String] = [""]
    @IBOutlet var table: UITableView!
    @IBOutlet weak var graphView: GraphView!
    var isGraphViewShowing = false
   
    
    
    @IBAction func counterViewTap(gesture:UITapGestureRecognizer?) {
        if (isGraphViewShowing) {
            
            //hide Graph
            UIView.transitionFromView(graphView,
                toView: table,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromLeft , UIViewAnimationOptions.ShowHideTransitionViews],
                completion:nil)
        } else {
            setupGraphDisplay()
            //show Graph
            UIView.transitionFromView(table,
                toView: graphView,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromRight , UIViewAnimationOptions.ShowHideTransitionViews],
                completion: nil)
        }
        isGraphViewShowing = !isGraphViewShowing
    }
    
    
    func setupGraphDisplay() {
//        
//        //Use 7 days for graph - can use any number,
//        //but labels and sample data are set up for 7 days
//        let noOfDays:Int = 7
//        
//        //1 - replace last day with today's actual data
//        graphView.graphPoints[graphView.graphPoints.count-1] = counterView.counter
//        
//        //2 - indicate that the graph needs to be redrawn
//        graphView.setNeedsDisplay()
//        
//        maxLabel.text = "\(graphView.graphPoints.maxElement()!)"
//        
//        //3 - calculate average from graphPoints
//        let average = graphView.graphPoints.reduce(0, combine: +) / graphView.graphPoints.count
//        averageWaterDrunk.text = "\(average)"
//        
//        //set up labels
//        //day of week labels are set up in storyboard with tags
//        //today is last day of the array need to go backwards
//        
//        //4 - get today's day number
//        let dateFormatter = NSDateFormatter()
//        let calendar = NSCalendar.currentCalendar()
//        let componentOptions:NSCalendarUnit = .Weekday
//        let components = calendar.components(componentOptions,
//            fromDate: NSDate())
//        var weekday = components.weekday
//        
//        let days = ["S", "S", "M", "T", "W", "T", "F"]
//        
//        //5 - set up the day name labels with correct day
//        for i in (1...days.count).reverse() {
//            if let labelView = graphView.viewWithTag(i) as? UILabel {
//                if weekday == 7 {
//                    weekday = 0
//                }
//                labelView.text = days[weekday--]
//                if weekday < 0 {
//                    weekday = days.count - 1
//                }
//            }
//        }
    }
    
    func methodOfReceivedNotification(notification: NSNotification){
        self.personsName =  GTDBManager.sharedInstance.getTotalStatText()
        self.personRate =  GTDBManager.sharedInstance.getTotalStatSubText(self.typeStatic)
        self.table.reloadData()
        
        print("hello")
       NSNotificationCenter.defaultCenter().removeObserver(self, name: "NotificationIdentifierStat", object: nil)
    }
   
   
    override func viewDidLoad() {
        super.viewDidLoad()
        if self.typeStatic == 1 {
        GTDBManager.sharedInstance.getLoadlStat(self.siteID)
        } else {
            GTDBManager.sharedInstance.getLoadlStatPersonId(self.siteID, personId: self.perosnId, startDate: self.startDate, endDate: self.endDate)
            print("start date = \(self.startDate) end date = \(self.endDate)")
        }
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "methodOfReceivedNotification:", name:"NotificationIdentifierStat", object: nil)
        

        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
            return personsName.count
     
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = self.table.dequeueReusableCellWithIdentifier("cell",forIndexPath: indexPath)
      
            cell.textLabel?.text = personsName[indexPath.row]
            cell.detailTextLabel?.text = personRate[indexPath.row]
        
        return cell
    }
   
}