//
//  GTStaticViewContrlor.swift
//  Quantisk
//
//  Created by iMac on 09.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit

class GTStaticViewController: UIViewController,UITableViewDataSource, UITableViewDelegate {
    var typeStatic:Int = 0
    var siteID:Int = 0
    var perosnId:Int = 0
    var startDate: String = ""
    var endDate: String = ""
    
    var personsName: [String] = [""]
    var personRate: [String] = [""]
    @IBOutlet var table: UITableView!
   
    
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