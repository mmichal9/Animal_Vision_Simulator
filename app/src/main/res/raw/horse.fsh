#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


void mainImage( out vec4 fragColor, in vec2 fragCoord )

{
    vec2 uv = fragCoord.xy/ iResolution.xy;
	vec4 col = texture2D( iChannel0, uv );

    //Horse Vision

	vec3 c_r = vec3(0.525, 0.475, 0.);
	vec3 c_g = vec3(0.65, 0.25, 0.1);
	vec3 c_b = vec3(0.1, 0.40, 0.50);

	vec3 rgb = vec3( dot(col.rgb ,c_r), dot(col.rgb ,c_g), dot(col.rgb ,c_b));

	fragColor = vec4(rgb,1.0);
}


void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
