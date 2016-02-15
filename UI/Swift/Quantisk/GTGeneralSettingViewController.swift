//
//  GTGeneralSettingViewController.swift
//  Quantisk
//
//  Created by iMac on 09.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import UIKit

class GTGeneralSettingViewController: UIViewController , UIPickerViewDelegate, UIPickerViewDataSource{
      var type: Int = 0
    @IBOutlet var userName: UINavigationItem!
    @IBOutlet var imageToDate: UIImageView!
    @IBOutlet var imageFromDate: UIImageView!
    @IBAction func getStatistic(sender: AnyObject) {
  
     
      let vc = self.storyboard!.instantiateViewControllerWithIdentifier("static") as! GTStaticViewController
        vc.typeStatic = self.type
        print(self.type)
        self.navigationController!.pushViewController(vc, animated: true)

        
        
    }
    @IBOutlet var toDatePicker: UIDatePicker!
    @IBOutlet var fromDatePicker: UIDatePicker!
    @IBOutlet var personPicker: UIPickerView!
    @IBOutlet var sitePicker: UIPickerView!
    @IBOutlet var typeStatPicker: UIPickerView!

    
    
    let typeStat = ["Daily Statistics","General Statistics"]
    var site = GTDBManager.sharedInstance.getAllSites()
    var persons = GTDBManager.sharedInstance.getAllPersons()
    
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == typeStatPicker{
            return typeStat.count
        } else  if pickerView == sitePicker{
            return site.count
        } else{
             return persons.count
        }
    }
    //MARK: Delegates
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == typeStatPicker{
            self.type = row
            return typeStat[row]
        } else  if pickerView == sitePicker{
            return site[row]
        } else{
            return persons[row]
        }
    }
    

//    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
//        return pickerData[row]
//    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView == typeStatPicker{
            if row == 0 {
                UIView.animateWithDuration(0.5, delay: 0.0, options: UIViewAnimationOptions.TransitionNone, animations: { () -> Void in
                    self.toDatePicker.alpha = 1
                    self.toDatePicker.enabled = false
                    self.fromDatePicker.alpha = 1
                    self.fromDatePicker.enabled = false
                    self.imageToDate.alpha = 1
                    self.imageFromDate.alpha = 1
                    
                    
                    }, completion: { (finished: Bool) -> Void   in     })

             
                
            } else if row == 1 {
                
                
                UIView.animateWithDuration(0.5, delay: 0.0, options: UIViewAnimationOptions.TransitionNone, animations: { () -> Void in
                    self.toDatePicker.alpha = 0
                    self.toDatePicker.enabled = true
                    self.fromDatePicker.alpha = 0
                    self.fromDatePicker.enabled = true
                    self.imageToDate.alpha = 0
                    self.imageFromDate.alpha = 0
                
                    
                    }, completion: { (finished: Bool) -> Void   in     })
                
                
                
                
            }
        }
        
            
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