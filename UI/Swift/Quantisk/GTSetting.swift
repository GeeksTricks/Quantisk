//
//  GTSetting.swift
//  Quantisk
//
//  Created by iMac on 18.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import RealmSwift




class GTSetting: Object{
    
    dynamic var ID: String = ""
    dynamic var Value: String = ""
    override static func primaryKey() -> String? {
        return "ID"
    }
    
    
    
}

