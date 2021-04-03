package com.example.justjava;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_box);
       boolean hasWhipped= checkBox.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate_box);
        boolean hasChocolate= checkBox2.isChecked();
        //Log.v("mainActivity", "Has Whipped Cream" + hasWhipped);
        EditText editText = (EditText) findViewById(R.id.name_view);
        String clientName = editText.getText().toString();
        int price = calculatePrice(quantity, hasWhipped, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhipped, hasChocolate, clientName);
        Intent intent = new Intent (Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only email apps va s'ocuper de l'envoi
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + clientName); //ici on a ajouter l'objet d'email
        intent.putExtra(Intent.EXTRA_TEXT,  priceMessage); //ici on a ajouter un corps à l'email: order summary qui affiche les details 

        if (intent.resolveActivity(getPackageManager()) != null) { //on test si une app that will handle the operation exist, sinon on va pas lancer l'intent
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }



    /**
     *This method is for calculating the price of the coffee
     * @param hasChocolate will cost the client 2 more dollars
     * @param hasWhipped will cost the client 1 more dollars
     * @return
     */
    private int calculatePrice(int quantity, boolean hasWhipped, boolean hasChocolate){
        int price;
        if (hasWhipped == true && hasChocolate == false){
            price = (quantity*5)+1;}
        else if (hasChocolate == true && hasWhipped == false){
            price = (quantity*5)+2;
        } else if (hasChocolate == true && hasWhipped == true){
            price = (quantity*5) + 3;
        } else {price = quantity*5;
        }
        return price;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhipped is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhipped, boolean addChocolate, String clientName){
        String priceMessage = clientName + "\n Quantity: " + quantity + "\n Whipped Cream? " + addWhipped + "\n Chocolate? "+ addChocolate + "\n Total: $ " + price + "\n Thank you! ";
        return priceMessage;
    }

    public void increment(View view) {
        if (quantity==100)
        {
            Toast.makeText(this, "You cannot order more than 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            //afficher un toast si le nbr est égal ou sup à 100!
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if (quantity==1) {
            Toast.makeText(this, "You cannot order less than one cup of coffee!", Toast.LENGTH_SHORT).show();
            //afficher un toast si le nbr est égal ou inf à 1!
            return;
        }
        quantity = quantity -1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}