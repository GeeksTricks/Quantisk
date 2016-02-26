//
//  GTGrapViewController.swift
//  Quantisk
//
//  Created by iMac on 25.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit

class GTGrapViewController: UIViewController {
    var typeStatic:Int = 0
    var siteID:Int = 0
    @IBOutlet var userNameLabel: UILabel!
    var perosnId:Int = 0
    var startDate: String = ""
    var endDate: String = ""
    
    var personsName: [String] = [""]
    var personRate: [String] = [""]
    
    
    
    
        
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
    self.userNameLabel.text = personsName[0]
    
    NSNotificationCenter.defaultCenter().removeObserver(self, name: "NotificationIdentifierStat", object: nil)
    
    self.drawX()
    
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

}