//
//  GTPersons.swift
//  Quantisk
//
//  Created by iMac on 11.02.16.
//  Copyright © 2016 GeeksTricks. All rights reserved.
//

import Foundation
import RealmSwift




class GTPersons: Object{
    
    dynamic var ID: Int = -1
    dynamic var Name: String = ""
    override static func primaryKey() -> String? {
        return "Name"
 }



}

