from quantisk_api import app as application
import os 

ip = os.environ.get('OPENSHIFT_PYTHON_IP', '0.0.0.0') 
port = int(os.environ.get('OPENSHIFT_PYTHON_PORT', 5000)) 

if __name__ == '__main__': 
	application.run(debug=True, host=ip, port=port)