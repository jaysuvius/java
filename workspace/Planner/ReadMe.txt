Welcome to Jeremy's Planner Application for C195 Advanced Java Concepts

Please note a couple of the following instructions.

To login please login with the username "Jeremy" and the password "test"
They are both case sensitive so honor the capitalization

To select English or Espanol for the locale, use the dropdown combobox on the login form

When the user logs in, all future appointment reminders will be scheduled for 15 minutes prior to the appointment
date/time.  If less than 15 minutes remain unitil the appointment, a reminder will be displayed immediately

From the main menu you can

Manage Users
	Add, Edit, and Delete users here
	
Manage Customers
	Add Edit and Delete Customer here
	
Manage Addresses
	The manage address feature is launched from the customer detail form.  Simply click "Select Address"
	and the Add, Edit, Delete address form will be displayed allowing managment of Address data.  A similar
	pattern can then be followed to manage cities, and countries.  Click the add city/country button, and
	the management forms for these data elements will be displayed allowing management of the respective
	data elements.  Validation is implemented at each level.  When a data element is selected with the
	select button on the management form, the management form is closed and the detail form of the parent 
	is popultated with the selected child data.  This design pattern was selected as each data element has
	parent to child relationship with the next.
	
Manage Appointments
	The manage appointments screen follows the same pattern as manage customers.  Use the add button to add
	new appointments and the edit button to edit existing appointments.  When selecting a customer to attach
	to an appointment, the manage customer screen is displayed allowing the user to drill into address data
	from the customer detail if they choose.  When the appointment is saved

View Month Calendar
	This will dispaly a monthly calendar view of appointments, and the arrows at the top will allow the user
	to traverse through months.
	
View Week Calendar
	This will dispaly a weekly calendar view of appointments, and the arrows at the top will allow the user
	to traverse through weeks.
	
Appointment Report
	This will display a report of appointments by month and year, and each coloumn can be clicked for sorting
	
Customer Report
	This will display a report of all customers, and their associated addresses, if they exist as well as active
	status
