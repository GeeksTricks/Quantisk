//
//  GTSettingViewController.swift
//  Quantisk
//
//  Created by iMac on 09.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation

import UIKit

class GTSettingViewController: UIViewController {
    
    @IBAction func updateSites(sender: AnyObject) {
    }
    @IBAction func updatePersons(sender: AnyObject) {
        GTDBManager.sharedInstance.refreshPersons()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}