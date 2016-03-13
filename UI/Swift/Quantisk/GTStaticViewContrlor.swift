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
    var personRateCount : [String] = [""]
    @IBOutlet var table: UITableView!
    @IBOutlet weak var graphView: GTGraphStat!
    var isGraphViewShowing = false
   @IBOutlet weak var namePerson: UILabel!
    
    
       
    
    
    
    //transmision view
    
    @IBAction func counterViewTap(gesture:UITapGestureRecognizer?) {
        if (isGraphViewShowing) {
            
            //hide Graph
            UIView.transitionFromView(graphView,
                toView: table,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromLeft , UIViewAnimationOptions.ShowHideTransitionViews],
                completion:nil)
        } else {
          
            //show Graph
            UIView.transitionFromView(table,
                toView: graphView,
                duration: 1.0,
                options: [UIViewAnimationOptions.TransitionFlipFromRight , UIViewAnimationOptions.ShowHideTransitionViews],
                completion: nil)
        }
        isGraphViewShowing = !isGraphViewShowing
    }
    
    
   
    //func Notification
    func methodOfReceivedNotification(notification: NSNotification){
        self.personsName =  GTDBManager.sharedInstance.getTotalStatText()
        self.personRate =  GTDBManager.sharedInstance.getTotalStatSubText(self.typeStatic)
        self.graphView.graphPoints = GTDBManager.sharedInstance.getTotalStatSubText(1)
        
        
        
        self.table.reloadData()
        
        
       NSNotificationCenter.defaultCenter().removeObserver(self, name: "NotificationIdentifierStat", object: nil)
    }
   
   
    override func viewDidLoad() {
        super.viewDidLoad()
        
        namePerson.text = GTDBManager.sharedInstance.getPersonNameFromId(perosnId)
        if self.typeStatic == 1 {
        GTDBManager.sharedInstance.getLoadlStat(self.siteID)
        } else {
            GTDBManager.sharedInstance.getLoadlStatPersonId(self.siteID, personId: self.perosnId, startDate: self.startDate, endDate: self.endDate)
          
        }
        //add notification
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