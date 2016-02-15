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
    
    @IBOutlet var table: UITableView!
    var typeStatic:Int = 0
    
    
   
    
    var persons: [String] = ["11/02/2016 - 14", "12/02/2016 - 15", "13/02/2016 - 16", "14/02/2016 - 17"]
   
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.typeStatic == 0{
            return persons.count
        } else{
        
        return 1;
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:UITableViewCell = self.table.dequeueReusableCellWithIdentifier("cell",forIndexPath: indexPath)
        cell.textLabel?.text = "Petrov"
        print(self.typeStatic)
        if self.typeStatic == 0{
            cell.detailTextLabel?.text = persons[indexPath.row]
            
        } else{
            
             cell.detailTextLabel?.text = "56"
        }
        return cell
    }
   
}